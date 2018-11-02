import "./graph-content.scss";

import React from "react";
import PropTypes from "prop-types";
import * as d3 from "d3";

export default class GraphContent extends React.Component {

    static propTypes = {
        data: PropTypes.shape({
            links: PropTypes.arrayOf(PropTypes.shape({
                source: PropTypes.string.isRequired,
                target: PropTypes.string.isRequired,
            })),
            nodes: PropTypes.arrayOf(PropTypes.shape({
                name: PropTypes.string.isRequired,
                color: PropTypes.string.isRequired
            }))
        }),
        handleLinkClick: PropTypes.func,
        handleNodeClick: PropTypes.func,
        height: PropTypes.number,
        width: PropTypes.number
    };

    componentDidMount() {
        this.renderGraph();
    }

    render() {
        const {width, height} = this.props;
        return (
            <div>
                <svg
                    id="graph-content"
                    width={width}
                    height={height}
                />
                {this.renderGraph()}
            </div>);
    }

    renderGraph() {
        this.linksData = _.cloneDeep(this.props.data.links);
        this.nodesData = _.cloneDeep(this.props.data.nodes);
        this.createSimulation();
        this.createLinks();
        this.createNodes();
        this.simulation.on("tick", this.handleTick.bind(this));
        this.initZoom();
        this.buildArrow();
    }

    get svg() {
        return d3.select("#graph-content");
    }

    createSimulation() {
        this.simulation = d3.forceSimulation()
            .nodes(this.nodesData)
            .force("charge", d3.forceManyBody().strength(-200))
            .force("center", d3.forceCenter(this.props.width / 2, this.props.height / 2))
            .force("links", d3.forceLink(this.linksData).id(d => d.name));
    }

    createNodes() {
        this.setMinNodeSize();
        this.setMaxNodeSize();
        const scaleRadius = d3.scaleSqrt().domain([this.minNodeSize, this.maxNodeSize]).range([5, 30]);
        this.nodesViewContainer = this.svg
            .append("g")
            .attr("class", "nodes");
        const nodes = this.nodesViewContainer
            .selectAll(".node")
            .data(this.nodesData)
            .enter()
            .append("g")
            .attr("class", "node")
            .on("click", this.handleNodeClick.bind(this));
        this.nodes = nodes
            .append("circle")
            .attr("r", node => scaleRadius(node.size))
            .attr("fill", (node) => node.color)
            .call(
                d3
                    .drag()
                    .on("drag", this.handleDrag.bind(this))
            );
        this.nodesLabels = nodes
            .append("text")
            .attr("class", "node-label")
            .attr("x", 12)
            .attr("dy", ".35em")
            .text(d => d.name);
    }

    setMinNodeSize() {
        if (_.isEmpty(this.nodesData)) {
            return;
        }
        this.minNodeSize = _.minBy(this.nodesData, (obj) => obj.size).size;
    }

    setMaxNodeSize() {
        if (_.isEmpty(this.nodesData)) {
            return;
        }
        this.maxNodeSize = _.maxBy(this.nodesData, (obj) => obj.size).size;
    }

    createLinks() {
        this.linksViewContainer = this.svg.append("g");
        this.links = this.linksViewContainer
            .attr("class", "links")
            .selectAll("line")
            .data(this.linksData)
            .enter().append("line")
            .attr("stroke-width", 4)
            .attr("marker-end", "url(#end)")
            .on("click", this.handleLinkClick.bind(this));
    }

    handleTick() {
        this.nodes
            .attr("cx", (d) => d.x)
            .attr("cy", (d) => d.y);

        this.nodesLabels
            .attr("x", (d) => d.x)
            .attr("dy", (d) => d.y);

        this.links
            .attr("x1", (d) => d.source.x)
            .attr("y1", (d) => d.source.y)
            .attr("x2", (d) => d.target.x)
            .attr("y2", (d) => d.target.y);
    }

    handleDrag(d) {
        d.x = d3.event.x;
        d.y = d3.event.y;
        this.nodes.filter(node => node.name === d.name).attr("cx", (d) => d.x).attr("cy", (d) => d.y);
        this.nodesLabels.filter(nodeLabel => nodeLabel.name === d.name).attr("x", (d) => d.x).attr("dy", (d) => d.y);
        this.links.filter(link => link.source.name === d.name).attr("x1", d.x).attr("y1", d.y);
        this.links.filter(link => link.target.name === d.name).attr("x2", d.x).attr("y2", d.y);
    }

    buildArrow() {
        this.svg.append("svg:defs")
            .selectAll("marker")
            .data(["end"])
            .enter().append("svg:marker")
            .attr("id", String)
            .attr("viewBox", "0 -5 10 10")
            .attr("refX", 15)
            .attr("refY", 0)
            .attr("markerWidth", 4)
            .attr("markerHeight", 3)
            .attr("orient", "auto")
            .append("svg:path")
            .attr("d", "M0,-5L10,0L0,5");
    }

    initZoom() {
        this.zoom = d3.zoom().on('zoom', () => {
            this.nodesViewContainer.attr('transform', d3.event.transform);
            this.linksViewContainer.attr('transform', d3.event.transform);
        });
        this.svg.call(this.zoom);
    }

    zoomTransition(zoomLevel) {
        this.svg.transition().duration(500)
            .call(this.zoom.scaleBy, zoomLevel);
    }

    handleNodeClick(event) {
        this.props.handleNodeClick(event.name);
    }

    handleLinkClick(event) {
        this.props.handleLinkClick(event);
    }

}