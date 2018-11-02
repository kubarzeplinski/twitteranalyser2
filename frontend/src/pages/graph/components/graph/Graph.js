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
                negativeUsersPercentage: PropTypes.number.isRequired,
                neutralUsers: PropTypes.number.isRequired,
                neutralUsersPercentage: PropTypes.number.isRequired,
                positiveUsers: PropTypes.number.isRequired,
                positiveUsersPercentage: PropTypes.number.isRequired,
                unknownUsers: PropTypes.number.isRequired,
                unknownUsersPercentage: PropTypes.number.isRequired,
                veryNegativeUsers: PropTypes.number.isRequired,
                veryNegativeUsersPercentage: PropTypes.number.isRequired,
                veryPositiveUsers: PropTypes.number.isRequired,
                veryPositiveUsersPercentage: PropTypes.number.isRequired,
            })
        }),
        handleLinkClick: PropTypes.func,
        handleNodeClick: PropTypes.func,
        handleUserDialogClose: PropTypes.func,
        isDataLoading: PropTypes.bool
    };

    render() {
        const {nodes, sentimentStatistics} = this.props.data;
        return (
            <div>
                <div className="card-label">
                    <h5>Users</h5>
                    <Tag className="bp3-large">
                        <span>{nodes.length >= 1 ? nodes.length - 1 : 0}</span>
                    </Tag>
                    <h5>Very Positive Users</h5>
                    <Tag className="bp3-large" style={{backgroundColor: "green"}}>
                        <span>{sentimentStatistics.veryPositiveUsers} ({sentimentStatistics.veryPositiveUsersPercentage}%)</span>
                    </Tag>
                    <h5>Positive Users</h5>
                    <Tag className="bp3-large" style={{backgroundColor: "lime"}}>
                        <span>{sentimentStatistics.positiveUsers} ({sentimentStatistics.positiveUsersPercentage}%)</span>
                    </Tag>
                    <h5>Neutral Users</h5>
                    <Tag className="bp3-large" style={{backgroundColor: "grey"}}>
                        <span>{sentimentStatistics.neutralUsers} ({sentimentStatistics.neutralUsersPercentage}%)</span>
                    </Tag>
                    <h5>Negative Users</h5>
                    <Tag className="bp3-large" style={{backgroundColor: "orange"}}>
                        <span>{sentimentStatistics.negativeUsers} ({sentimentStatistics.negativeUsersPercentage}%)</span>
                    </Tag>
                    <h5>Very Negative Users</h5>
                    <Tag className="bp3-large" style={{backgroundColor: "red"}}>
                        <span>{sentimentStatistics.veryNegativeUsers} ({sentimentStatistics.veryNegativeUsersPercentage}%)</span>
                    </Tag>
                    <h5>Unknown Users</h5>
                    <Tag className="bp3-large" style={{backgroundColor: "white", color: "black"}}>
                        <span>{sentimentStatistics.unknownUsers} ({sentimentStatistics.unknownUsersPercentage}%)</span>
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
