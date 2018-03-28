import React from "react";
import {Card} from "@blueprintjs/core";
import "./graph.scss"

export default class Graph extends React.Component {

    init() {
        const config = {
            caption: function (node) {
                return node.caption;
            },
            dataSource: 'data/contrib.json',
            divSelector: "#alchemy",
            forceLocked: false,
            graphHeight: function () {
                return 500;
            },
            graphWidth: function () {
                return 1454;
            },
            initialScale: 0.7,
            linkDistance: function () {
                return 40;
            },
        };
        alchemy = new Alchemy(config);
    }

    componentDidMount() {
        this.init();
    }

    render() {
        return (
            <Card className="graph-card">
                <div className="alchemy" id="alchemy"/>
            </Card>
        );
    }

}
