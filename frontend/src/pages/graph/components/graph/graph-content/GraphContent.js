import "./graph-content.scss";

import React from "react";
import PropTypes from "prop-types";
import * as d3 from "d3";

export default class GraphContent extends React.Component {

    static propTypes = {
        handleNodeClick: PropTypes.func,
        height: PropTypes.number,
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
        this.links = _.cloneDeep(this.props.data.links);
        this.nodes = _.cloneDeep(this.props.data.nodes);
        this.createSimulation();
        this.createLinks();
        this.createNodes();
        this.simulation.on("tick", this.handleTick.bind(this));
        this.initZoom();
    }

    get svg() {
        return d3.select("#graph-content");
    }

    createSimulation() {
        this.simulation = d3.forceSimulation()
            .nodes(this.nodes)
            .force("charge", d3.forceManyBody().strength(-200))
            .force("center", d3.forceCenter(this.props.width / 2, this.props.height / 2))
            .force("links", d3.forceLink(this.links).id(d => d.name));
    }

    createNodes() {
        this.nodesViewContainer = this.svg
            .append("g")
            .attr("class", "nodes");
        const nodes = this.nodesViewContainer
            .selectAll(".node")
            .data(this.nodes)
            .enter()
            .append("g")
            .attr("class", "node")
            .on("click", this.handleNodeClick.bind(this));
        this.node = nodes
            .append("circle")
            .attr("r", 5)
            .attr("fill", (node) => node.color);
        this.nodeLabel = nodes
            .append("text")
            .attr("class", "node-label")
            .attr("x", 12)
            .attr("dy", ".35em")
            .text(d => d.name);
    }

    createLinks() {
        this.linksViewContainer = this.svg.append("g");
        this.link = this.linksViewContainer
            .attr("class", "links")
            .selectAll("line")
            .data(this.links)
            .enter().append("line")
            .attr("stroke-width", 2);
    }

    handleTick() {
        this.node
            .attr("cx", (d) => d.x)
            .attr("cy", (d) => d.y);

        this.nodeLabel
            .attr("x", (d) => d.x)
            .attr("dy", (d) => d.y);

        this.link
            .attr("x1", (d) => d.source.x)
            .attr("y1", (d) => d.source.y)
            .attr("x2", (d) => d.target.x)
            .attr("y2", (d) => d.target.y);
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

}