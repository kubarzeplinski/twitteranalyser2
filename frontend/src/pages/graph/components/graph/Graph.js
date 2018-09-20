import "./graph.scss"

import React from "react";
import PropTypes from "prop-types";
import {Card, Intent, Spinner, Tag} from "@blueprintjs/core";
import GraphContent from "./GraphContent";

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
        return <GraphContent {...this.props} />;
    }

}
