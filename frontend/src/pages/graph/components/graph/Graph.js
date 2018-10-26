import "./graph.scss"

import React from "react";
import PropTypes from "prop-types";
import {Card, Intent, Spinner, Tag} from "@blueprintjs/core";
import GraphContent from "./graph-content/GraphContent";
import GraphNavigation from "./graph-navigation/GraphNavigation";

export default class Graph extends React.Component {

    static propTypes = {
        data: PropTypes.shape({
            links: PropTypes.arrayOf(PropTypes.shape({
                source: PropTypes.string.isRequired,
                target: PropTypes.string.isRequired,
            })),
            nodes: PropTypes.arrayOf(PropTypes.shape({
                name: PropTypes.string.isRequired,
                color: PropTypes.string.isRequired
            })),
            sentimentStatistics: PropTypes.shape({
                negativeUsers: PropTypes.number.isRequired,
                neutralUsers: PropTypes.number.isRequired,
                positiveUsers: PropTypes.number.isRequired,
                veryNegativeUsers: PropTypes.number.isRequired,
                veryPositiveUsers: PropTypes.number.isRequired,
            })
        }),
        isDataLoading: PropTypes.bool,
        handleNodeClick: PropTypes.func,
        handleUserDialogClose: PropTypes.func
    };

    render() {
        return (
            <div>
                <div className="card-label">
                    <h5>Users</h5>
                    <Tag className="bp3-large">
                        <span>{this.props.data.nodes.length >= 1 ? this.props.data.nodes.length - 1 : 0}</span>
                    </Tag>
                    <h5>Very Positive Users</h5>
                    <Tag className="bp3-large" style={{backgroundColor: "green"}}>
                        <span>{this.props.data.sentimentStatistics.veryPositiveUsers}</span>
                    </Tag>
                    <h5>Positive Users</h5>
                    <Tag className="bp3-large" style={{backgroundColor: "lime"}}>
                        <span>{this.props.data.sentimentStatistics.positiveUsers}</span>
                    </Tag>
                    <h5>Neutral Users</h5>
                    <Tag className="bp3-large" style={{backgroundColor: "grey"}}>
                        <span>{this.props.data.sentimentStatistics.neutralUsers}</span>
                    </Tag>
                    <h5>Negative Users</h5>
                    <Tag className="bp3-large" style={{backgroundColor: "orange"}}>
                        <span>{this.props.data.sentimentStatistics.negativeUsers}</span>
                    </Tag>
                    <h5>Very Negative Users</h5>
                    <Tag className="bp3-large" style={{backgroundColor: "red"}}>
                        <span>{this.props.data.sentimentStatistics.veryNegativeUsers}</span>
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
                    <Spinner intent={Intent.PRIMARY} className="bp3-large"/>
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
                height={475}
            />
        );
    }

}
