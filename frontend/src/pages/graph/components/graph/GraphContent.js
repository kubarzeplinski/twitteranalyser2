import React from "react";
import "./graph-content.scss";
import * as d3 from "d3";
import _ from 'lodash';
import PropTypes from 'prop-types';

export default class GraphContent extends React.PureComponent {

    static propTypes = {
        id: PropTypes.string,
        data: PropTypes.array,
        width: PropTypes.number,
        height: PropTypes.number,
        backgroundColor: PropTypes.string,
        groupLabelColor: PropTypes.string,
        groupLabelFontSize: PropTypes.number,
        groupLabelFormatter: PropTypes.func,
        onMouseEnterNode: PropTypes.func,
        onMouseLeaveNode: PropTypes.func,
        onMouseClick: PropTypes.func,
        onMouseClickRemove: PropTypes.func,
        radiusForceFactor: PropTypes.number,
        skipSimulationTick: PropTypes.number,
        initClickListener: PropTypes.bool
    };

    static defaultProps = {
        id: 'graph',
        data: [],
        width: 0,
        height: 0,
        backgroundColor: 'white',
        groupLabelColor: '#000000',
        groupLabelFontSize: 18,
        onMouseEnterNode: _.noop,
        onMouseLeaveNode: _.noop,
        onMouseClick: _.noop,
        onMouseClickRemove: _.noop,
        radiusForceFactor: 0.95,
        skipSimulationTick: 1,
        initClickListener: true
    };

    componentDidMount() {
        this.createView();
        this.initZoom();
        this.createSimulation();
        this.initialScale = this.prepareInitialScale();
        this.initializeBubbles();
    }

    componentWillUnmount() {
        this.clear();
    }

    clear() {
        this.clearAnimation();
        this.clickedIndex = null;
        this.removeActiveNodeClickListener();
    }

    clearAnimation() {
        this.svg.interrupt();
        this.stopSimulation();
    }

    componentDidUpdate() {
        if (this.isNewDataAvailable() || this.isResized()) {
            this.clear();
            this.initialScale = this.prepareInitialScale();
            if (this.initialScale >= this.currentScale) {
                this.initializeBubbles();
            } else {
                this.setInitialViewPerspective(() => this.initializeBubbles());
            }
        }
    }

    isNewDataAvailable() {
        return !_.isEqual(this.data, this.props.data);
    }

    isResized() {
        return this.width !== this.props.width || this.height !== this.props.height;
    }

    initializeBubbles() {
        this.cacheData();
        this.clearCircles();
        this.clearGroupLabels();
        if (!_.isEmpty(this.props.data)) {
            this.createCircles();
            this.restartSimulation();
        }
    }

    cacheData() {
        this.data = this.props.data;
        this.width = this.props.width;
        this.height = this.props.height;
    }

    get isSingleGroupMode() {
        return true;
    }

    createFilter() {
        this.defs = this.svg.append("defs");
        this.filter = this.defs.append("filter")
            .attr("id", "drop-shadow")
            .attr("height", "120%");
        this.filter.append("feGaussianBlur")
            .attr("in", "SourceGraphic")
            .attr("stdDeviation", 0.5)
            .attr("result", "blurGraph");
        this.filter.append("feGaussianBlur")
            .attr("in", "SourceAlpha")
            .attr("stdDeviation", 1)
            .attr("result", "blurAlpha");
        this.feMerge = this.filter.append("feMerge");
        this.feMerge.append("feMergeNode")
            .attr("in", "blurAlpha");
        this.feMerge.append("feMergeNode")
            .attr("in", "blurGraph");
        this.feMerge.append("feMergeNode")
            .attr("in", "SourceGraphic");
    }

    createView() {
        this.svg = d3.select(`#${this.props.id} svg`);
        this.createFilter();
        this.svg.append("rect")
            .attr("width", this.props.width)
            .attr("height", this.props.height)
            .style('fill', this.props.backgroundColor);
        this.viewContainer = this.svg.append("g");
        this.view = this.viewContainer.append("g")
            .attr("class", "view");
    }

    initZoom() {
        this.zoom = d3.zoom().on('zoom', () => {
            this.viewContainer.attr('transform', d3.event.transform);
        });
        this.svg.call(this.zoom);
    }

    zoomTransition(zoomLevel) {
        this.clearAnimation();
        this.svg.transition().duration(500)
            .call(this.zoom.scaleBy, zoomLevel);
    }

    moveTransition(dx, dy) {
        this.clearAnimation();
        this.svg.transition().duration(500)
            .call(this.zoom.translateBy, dx, dy);
    }

    setInitialViewPerspective(callback) {
        this.svg.transition().duration(1000)
            .call(this.zoom.scaleTo, this.initialScale)
            .on('end', callback);
    }

    prepareInitialScale() {
        const approximateField = this.calculateNodesField(this.props.data) * (this.isSingleGroupMode ? 2.0 : 3.5);
        const viewField = this.props.height * this.props.width;
        const scale = Math.sqrt(viewField) / Math.sqrt(approximateField);
        return Math.min(scale, 1);
    }

    get currentScale() {
        return d3.zoomTransform(this.svg.node()).k;
    }

    calculateNodesField(data) {
        return _.chain(data)
            .map((d) => Math.pow(d.radius, 2) * Math.PI)
            .sumBy()
            .value();
    }

    getNodePosition() {
        return this.centerPosition;
    }

    get centerPosition() {
        return this.createPosition(this.props.width / 2, this.props.height / 2);
    }

    createPosition(x, y, row, column) {
        return {x, y, row, column};
    }

    restoreDefaultColor(d, i, circles) {
        if (_.isNil(i)) {
            d3.selectAll(circles)
                .select('circle')
                .style('fill', (e) => e.color);
        } else {
            d3.select(circles[i])
                .select('circle')
                .style('fill', _.isNil(this.clickedIndex) || this.clickedIndex === i ? d.color : d.opacityColor);
        }
    }

    removeActiveNodeClickListener() {
        if (this.activeNodeClickListener) {
            window.removeEventListener('click', this.activeNodeClickListener);
            this.activeNodeClickListener = null;
        }
    }

    clickedIndex = null;

    activeNodeClickListener = null;

    nodeClickListener = (d, i, circles) => {
        this.removeActiveNodeClickListener();
        d3.selectAll(circles.filter((e, index) => index !== i))
            .select('circle')
            .style('fill', (d) => d.opacityColor);
        this.clickedIndex = i;
        this.activeNodeClickListener = () => {
            this.clickedIndex = null;
            this.restoreDefaultColor(null, null, circles);
            this.removeActiveNodeClickListener();
            this.props.onMouseClickRemove(d, i, circles);
        };
        setTimeout(() => window.addEventListener('click', this.activeNodeClickListener), 0);
        this.props.onMouseClick(d, i, circles);
    };

    nodeMouseOverListener = (d, i, circles) => {
        d3.select(circles[i])
            .select('circle')
            .style('fill', d.hoverColor);
        this.props.onMouseEnterNode(d, i, circles);
    };

    nodeMouseLeaveListener = (d, i, circles) => {
        this.restoreDefaultColor(d, i, circles);
        this.props.onMouseLeaveNode(d, i, circles);
    };

    createCircles() {
        this.nodes = this.view.selectAll(".node")
            .data(this.props.data)
            .enter()
            .append("g")
            .attr("class", "node")
            .on("click", this.props.initClickListener ? this.nodeClickListener : _.noop)
            .on("mouseover", this.nodeMouseOverListener)
            .on("mouseleave", this.nodeMouseLeaveListener);
        this.nodes.append("circle")
            .style("fill", (d) => d.color)
            .style("filter", "url(#drop-shadow)")
            .attr('r', (d) => d.radius);
        this.nodes.append("text")
            .style("fill", (d) => d.labelColor)
            .style("font-size", (d) => d.labelBaseFontSize);
        this.nodes.each((d, i, nodes) => {
            const node = d3.select(nodes[i]);
            this.prepareNodeLabel(node, d);
        });
    }

    clearGroupLabels() {
        this.view.selectAll(".group-label").remove();
    }

    clearCircles() {
        this.nodes = this.view.selectAll(".node").remove();
    }

    createSimulation() {
        this.simulation = d3.forceSimulation()
            .force('x', d3.forceX().strength(() => this.isSingleGroupMode ? 0.05 : 0.07).x((d) => this.getNodePosition(d.groupIndex).x))
            .force('y', d3.forceY().strength(0.08).y(this.calculateYPositionForNode))
            .force('collide', d3.forceCollide((d) => d.radius * this.props.radiusForceFactor).strength(0.1))
            .on('tick', this.onSimulationTick)
            .on('end', this.onSimulationEnd);
        this.stopSimulation();
    }

    calculateYPositionForNode = (d) => {
        const y = this.getNodePosition(d.groupIndex).y;
        return y - y / 4 * d.targetHorizontalPositionFactor;
    };

    restartSimulation() {
        this.simulationTick = 0;
        this.simulation.nodes(this.props.data);
        this.simulation.alpha(1).restart();
    }

    stopSimulation() {
        this.simulation.stop();
    }

    onSimulationTick = () => {
        this.simulationTick++;
        const {skipSimulationTick} = this.props;
        if (skipSimulationTick <= 1 || this.simulationTick === 1 || (this.simulationTick % skipSimulationTick === 0)) {
            this.nodes.attr('transform', (d) => 'translate(' + [d.x, d.y] + ')');
            this.correctViewPerspectiveTransition();
        }
    };

    onSimulationEnd = () => {
        this.correctViewPerspectiveZoom(1.05);
    };

    correctViewPerspective() {
        this.clearAnimation();
        this.correctViewPerspectiveTransition();
        this.correctViewPerspectiveZoom(1.02, () => {
            this.initialScale = this.currentScale;
            this.initializeBubbles();
        });
    }

    correctViewPerspectiveZoom = (factor, onEnd = _.noop) => {
        const {height, width} = this.props;
        const box = this.viewContainer.node().getBoundingClientRect();
        const scale = Math.min(width / box.width / factor, height / box.height / factor);
        this.svg.transition().duration(500)
            .call(this.zoom.scaleBy, scale)
            .on('end', onEnd);
    };

    correctViewPerspectiveTransition() {
        const box = this.viewContainer.node().getBBox();
        this.svg.call(this.zoom.translateTo, box.width / 2 + box.x, box.height / 2 + box.y);
    }

    prepareNodeLabel(node, nodeData) {
        if (!nodeData.label) {
            return;
        }
        const nodeText = node.select('text')
            .style("text-anchor", "middle");
        if (_.isArray(nodeData.label)) {
            nodeData.label.forEach((e, i) => {
                nodeText.append('tspan')
                    .text(e)
                    .attr('x', 0)
                    .style('font-size', `${nodeData.labelFontSizeEm[i]}em`);
            });
        } else {
            nodeText.text(nodeData.label);
        }
        const text = node.select('text');
        const circle = node.select('circle');
        let textWidth = text.node().getBoundingClientRect().width;
        let circleWidth = circle.node().getBoundingClientRect().width;
        let i = 0;
        let ready = false;
        while ((textWidth > circleWidth - 4 && i++ < 100) && !ready) {
            nodeData.labelBaseFontSize--;
            text.style('font-size', (d) => {
                if (d.labelBaseFontSize < 0.5) {
                    ready = true;
                    return '10px';
                }
                return d.labelBaseFontSize;
            });
            textWidth = text.node().getBoundingClientRect().width;
            circleWidth = circle.node().getBoundingClientRect().width;
        }
        if (_.isArray(nodeData.label)) {
            nodeText.selectAll('tspan').attr('y', (e, i) => `${nodeData.labelYPositionEmOffset[i]}em`);
        }
    }

    render() {
        return (
            <div id={this.props.id}>
                <svg className="graph-content" width={this.props.width} height={this.props.height}/>
            </div>
        );
    }

}
