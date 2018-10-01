import "./graph.scss"

import React from "react";
import PropTypes from "prop-types";
import {Card, Intent, Spinner, Tag} from "@blueprintjs/core";
import GraphContent from "./graph-content/GraphContent";
import GraphNavigation from "./graph-navigation/GraphNavigation";

export default class Graph extends React.Component {

    static propTypes = {
        links: PropTypes.arrayOf(PropTypes.shape({
            source: PropTypes.string.isRequired,
            target: PropTypes.string.isRequired,
        })),
        isDataLoading: PropTypes.bool,
        handleNodeClick: PropTypes.func,
        handleUserDialogClose: PropTypes.func
    };

    render() {
        return (
            <div>
                <div className="card-label">
                    <h5>Number of users</h5>
                    <Tag className="pt-large">
                        <span>{this.props.links.length}</span>
                    </Tag>
                </div>
                <GraphNavigation
                    onPlusClick={() => this.graphContent.zoomTransition(1.3)}
                    onMinusClick={() => this.graphContent.zoomTransition(0.7)}
                />
                <Card className="graph-card">
                    {this.prepareCardContent()}
                </Card>
            </div>
        );
    }

    prepareCardContent() {
        if (this.props.isDataLoading) {
            return (
                <div className="graph-spinner">
                    <Spinner intent={Intent.PRIMARY} className="pt-large"/>
                </div>
            );
        }
        return (
            <GraphContent
                ref={(e) => {
                    this.graphContent = e;
                }}
                {...this.props}
                width={1473}
                height={494}
            />
        );
    }

}
