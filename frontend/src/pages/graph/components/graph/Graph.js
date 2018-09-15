import "./graph.scss"

import React from "react";
import {Card} from "@blueprintjs/core";
import * as d3 from "d3";
import _ from "lodash";

const links = [
    {source: "Sarah", target: "Alice"},
    {source: "Eveie", target: "Alice"},
    {source: "Peter", target: "Alice"},
    {source: "Mario", target: "Alice"},
    {source: "James", target: "Alice"},
    {source: "Ted", target: "Alice"},
    {source: "AAA", target: "Alice"},
    {source: "P", target: "Alice"},
    {source: "Marco", target: "Alice"},
    {source: "James Rodrigues", target: "Alice"}
];

export default class Graph extends React.Component {

    componentDidMount() {
        const nodes = {};

        // Compute the distinct nodes from the links.
        _.forEach(links, (link) => {
            link.source = nodes[link.source] ||
                (nodes[link.source] = {name: link.source});
            link.target = nodes[link.target] ||
                (nodes[link.target] = {name: link.target});
            link.value = +link.value;
        });

        //TODO set width and height dynamically
        const width = 1490;
        const height = 494;

        const force = d3.layout.force()
            .nodes(d3.values(nodes))
            .links(links)
            .size([width, height])
            .linkDistance(100)
            .charge(-300)
            .on("tick", tick)
            .start();

        const svg = d3.select(".graph-chart")
            .attr("width", width)
            .attr("height", height);

        // build the arrow.
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

        // add the links and the arrows
        const path = svg.append("svg:g").selectAll("path")
            .data(force.links())
            .enter()
            .append("svg:path")
            .attr("class", "link")
            .attr("marker-end", "url(#end)");

        // define the nodes
        const node = svg.selectAll(".node")
            .data(force.nodes())
            .enter()
            .append("g")
            .attr("class", "node")
            .call(force.drag);

        // add the nodes
        node.append("circle")
            .attr("r", 10);

        // add the text
        node.append("text")
            .attr("x", 12)
            .attr("dy", "2em")
            .text(d => d.name);

        // add the curvy lines
        function tick() {
            path.attr("d", d => {
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

            node.attr("transform", d => "translate(" + d.x + "," + d.y + ")");
        }

    }

    render() {
        return (
            <Card className="graph-card">
                <svg className="graph-chart"/>
            </Card>
        );
    }

}
