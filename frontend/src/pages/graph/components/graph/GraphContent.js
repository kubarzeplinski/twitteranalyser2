import React from "react";
import PropTypes from "prop-types";
import _ from "lodash";
import * as d3 from "d3";

let NODE;
let PATH;

export default class GraphContent extends React.Component {

    static propTypes = {
        links: PropTypes.arrayOf(PropTypes.shape({
            source: PropTypes.string.isRequired,
            target: PropTypes.string.isRequired,
        })),
        isDataLoading: PropTypes.bool,
        handleNodeClick: PropTypes.func,
        handleUserDialogClose: PropTypes.func
    };

    componentDidMount() {
        this.renderGraph();
    }

    render() {
        return (
            <div>
                <svg className="graph-chart"/>
                {this.renderGraph()}
            </div>
        );
    }

    renderGraph() {
        const {links} = this.props;
        const linksCopy = _.cloneDeep(links);
        const nodes = {};

        this.computeDistinctNodes(linksCopy, nodes);
        //TODO set width and height dynamically
        const width = 1490;
        const height = 494;
        const force = this.forceLayout(linksCopy, nodes, width, height);
        const svg = this.setWidthAndHeight(width, height);
        this.buildArrow(svg);
        this.addLinksAndArrows(svg, force);
        this.defineNodes(svg, force);
    }

    computeDistinctNodes(links, nodes) {
        _.forEach(links, (link) => {
            link.source = nodes[link.source] || (nodes[link.source] = {name: link.source});
            link.target = nodes[link.target] || (nodes[link.target] = {name: link.target});
        });
    }

    setWidthAndHeight(width, height) {
        return d3.select(".graph-chart")
            .attr("width", width)
            .attr("height", height);
    }

    forceLayout(links, nodes, width, height) {
        return d3.layout.force()
            .nodes(d3.values(nodes))
            .links(links)
            .size([width, height])
            .linkDistance(100)
            .charge(-300)
            .on("tick", this.handleTick)
            .start();
    }

    buildArrow(svg) {
        svg.append("svg:defs")
            .selectAll("marker")
            .data(["end"])      // Different link/path types can be defined here
            .enter().append("svg:marker")    // This section adds in the arrows
            .attr("id", String)
            .attr("viewBox", "0 -5 10 10")
            .attr("refX", 15)
            .attr("refY", -1.5)
            .attr("markerWidth", 10)
            .attr("markerHeight", 10)
            .attr("orient", "auto")
            .append("svg:path")
            .attr("d", "M0,-5L10,0L0,5");
    }

    addLinksAndArrows(svg, force) {
        PATH = svg.append("svg:g").selectAll("path")
            .data(force.links())
            .enter()
            .append("svg:path")
            .attr("class", "link")
            .attr("marker-end", "url(#end)");
    }

    handleNodeClick(event) {
        this.props.handleNodeClick(event.name);
    }

    defineNodes(svg, force) {
        NODE = svg.selectAll(".node")
            .data(force.nodes())
            .enter()
            .append("g")
            .attr("class", "node")
            .on("click", this.handleNodeClick.bind(this))
            .call(force.drag);

        NODE.append("circle")
            .attr("r", 10);

        NODE.append("text")
            .attr("x", 12)
            .attr("dy", "2em")
            .text(d => d.name);
    }

    handleTick() {
        PATH.attr("d", d => {
            const dx = d.target.x - d.source.x;
            const dy = d.target.y - d.source.y;
            const dr = Math.sqrt(dx * dx + dy * dy);
            return "M" +
                d.source.x + "," +
                d.source.y + "A" +
                dr + "," + dr + " 0 0,1 " +
                d.target.x + "," +
                d.target.y;
        });

        NODE.attr("transform", d => "translate(" + d.x + "," + d.y + ")");
    }

}