import Controls from "./components/controls/ControlsContainer";
import Graph from "./components/graph/GraphContainer";
import React from "react";
import {Button, Dialog, Classes} from "@blueprintjs/core";
import {IconNames} from "@blueprintjs/icons";
import PropTypes from "prop-types";
import moment from "moment";

export default class GraphPage extends React.Component {

    static propTypes = {
        isInfoDialogOpen: PropTypes.bool,
        handleInfoButtonClick: PropTypes.func,
        handleInfoDialogClose: PropTypes.func,
        handleKeywordsFetch: PropTypes.func,
        userData: PropTypes.object
    };

    render() {
        const {
            handleInfoButtonClick,
            isInfoDialogOpen,
            handleInfoDialogClose,
            handleKeywordsFetch,
        } = this.props;
        handleKeywordsFetch();
        return (
            <div className="panel-content">
                <h4>
                    Graph
                    <Button
                        className="bp3-minimal"
                        icon={IconNames.INFO_SIGN}
                        onClick={handleInfoButtonClick}
                    />
                    <Dialog
                        icon={IconNames.GRAPH}
                        isOpen={isInfoDialogOpen}
                        onClose={handleInfoDialogClose}
                        title="Graph"
                    >
                        <div className={Classes.DIALOG_BODY}>
                            <p>
                                Graph page is a place where you can analyze historical Twitter data from your sessions
                                from Statistics page.
                            </p>
                            <p>
                                Start analysis with selected keyword from dropdown and press search button.
                            </p>
                            <p>
                                Then you can analyse graph with user nodes and relationships.
                            </p>
                            <p>
                                Relationships contain information about tweets.
                            </p>
                            <p>
                                Color of nodes is determined by user tweets for this keyword and result is calculated
                                with Stanford Natural Language Processing sentiment analysis.
                            </p>
                            <p>
                                Click on nodes to fetch more information about user.
                            </p>
                            <p>
                                If you want to start analysis for different keyword please select another keyword from
                                dropdown and click search button.
                            </p>
                            <p>
                                <b>Enjoy Twitter data presented on directed graph!!!</b>
                            </p>
                        </div>
                    </Dialog>
                    {this.renderUserDialog()}
                </h4>
                <Controls/>
                <Graph/>
            </div>
        );
    }

    renderUserDialog() {
        const {isUserDialogOpen, handleUserDialogClose, userData} = this.props;
        const createdAtDate = moment(userData.createdAt).format("DD/MM/YYYY").valueOf();
        return (
            <Dialog
                icon={IconNames.USER}
                isOpen={isUserDialogOpen}
                onClose={handleUserDialogClose}
                title={`${userData.screenName}`}
            >
                <div className={Classes.DIALOG_BODY}>
                    <b>Account created at:</b> {createdAtDate}
                    <br/>
                    <b>Account description:</b> {userData.description}
                    <br/>
                    <b>Number of favourites:</b> {userData.favouritesCount}
                    <br/>
                    <b>Number of followers:</b> {userData.followersCount}
                    <br/>
                    <b>Number of friends:</b> {userData.friendsCount}
                    <br/>
                    <b>User id:</b> {userData.userId}
                    <br/>
                    <b>Language:</b> {userData.lang}
                    <br/>
                    <b>Location:</b> {userData.location}
                    <br/>
                    <b>Time zone:</b> {userData.timeZone}
                </div>
            </Dialog>
        );
    }

}