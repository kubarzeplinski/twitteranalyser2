import React from "react";
import PropTypes from 'prop-types';
import _ from "lodash";
import "whatwg-fetch";
import Controls from "./components/controls/ControlsContainer";
import {IconNames} from "@blueprintjs/icons";
import {Button, Card, Classes, Dialog, Elevation, Intent, Tag} from "@blueprintjs/core";

let stompClient = null;

export default class StatisticsPage extends React.Component {

    static propTypes = {
        isInfoDialogOpen: PropTypes.bool,
        handleInfoButtonClick: PropTypes.func,
        handleInfoDialogClose: PropTypes.func
    };

    constructor(args) {
        super(args);
        this.componentDidMount = this.componentDidMount.bind(this);
        this.state = {
            data: []
        }
    }

    componentDidMount() {
        const socket = new SockJS('http://localhost:8080/twitter-analyser');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, (frame) => {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/statistics/statisticsData', (data) => {
                console.log("data", data.body);
                this.setState({data: JSON.parse(data.body)});
            });
        });
    }

    render() {
        const {handleInfoButtonClick, isInfoDialogOpen, handleInfoDialogClose} = this.props;
        return (
            <div className="panel-content">
                <h4>
                    Statistics
                    <Button
                        className="pt-minimal"
                        icon={IconNames.INFO_SIGN}
                        onClick={handleInfoButtonClick}
                    />
                    <Dialog
                        icon={IconNames.CHART}
                        isOpen={isInfoDialogOpen}
                        onClose={handleInfoDialogClose}
                        title="Statistics"
                    >
                        <div className={Classes.DIALOG_BODY}>
                            <p>
                                Statistics page is a place where you can analyze twitter live streaming data by every
                                keyword.
                            </p>
                        </div>
                    </Dialog>
                </h4>
                <Controls/>
                <div>
                    <Card elevation={Elevation.TWO}>
                        <h4>Latest 5 users</h4>
                        {this.prepareLatest5Users()}
                    </Card>
                    <Card elevation={Elevation.TWO}>
                        <h4>Number of users</h4>
                        {this.prepareNumberOfUsersUsers()}
                    </Card>
                </div>
                <div>
                    <Card elevation={Elevation.TWO}>
                        <h4>Top 5 locations</h4>
                        {this.prepareTop5Locations()}
                    </Card>
                    <Card elevation={Elevation.TWO}>
                        <h4>Top 5 users by followers</h4>
                        {this.prepareTop5UsersByFollowers()}
                    </Card>
                </div>
                <div>
                    <Card elevation={Elevation.TWO} className="large">
                        <h4>Latest 5 tweets</h4>
                        {this.prepareLatest5Tweets()}
                    </Card>
                </div>
            </div>
        );
    }

    prepareLatest5Users() {
        const {latest5Users} = this.state.data;
        if (_.isUndefined(latest5Users)) {
            return;
        }
        return _.map(latest5Users, (user) =>
            <Tag key={user.userId} className="bp3-large" intent={Intent.PRIMARY}>
                <span>{user.screenName}</span>
            </Tag>
        );
    }

    prepareNumberOfUsersUsers() {
        const {numberOfUsers} = this.state.data;
        if (_.isUndefined(numberOfUsers)) {
            return;
        }
        return (
            <Tag className="bp3-large">
                <span>{numberOfUsers}</span>
            </Tag>
        );
    }

    prepareTop5Locations() {
        const {top5Locations} = this.state.data;
        if (_.isUndefined(top5Locations)) {
            return;
        }
        return _.map(top5Locations, (location) =>
            <Tag key={location} className="bp3-large" intent={Intent.SUCCESS}>
                <span>{location}</span>
            </Tag>
        );
    }

    prepareTop5UsersByFollowers() {
        const {top5UsersByFollowers} = this.state.data;
        if (_.isUndefined(top5UsersByFollowers)) {
            return;
        }
        return _.map(top5UsersByFollowers, (user) =>
            <Tag key={user.userId} className="bp3-large" intent={Intent.WARNING}>
                <span>{user.screenName}</span>
            </Tag>
        );
    }

    prepareLatest5Tweets() {
        const {latest5InterestedInRelations} = this.state.data;
        if (_.isUndefined(latest5InterestedInRelations)) {
            return;
        }
        let counter = 0;
        return _.map(latest5InterestedInRelations, (relation) =>
            <Tag key={counter++} className="bp3-large" intent={Intent.PRIMARY}>
                <span>{relation}</span>
            </Tag>
        );
    }

}