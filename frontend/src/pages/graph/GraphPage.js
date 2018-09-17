import Controls from "./components/controls/ControlsContainer";
import Graph from "./components/graph/GraphContainer";
import React from "react";
import {Button, Dialog} from "@blueprintjs/core";
import * as IconNames from "@blueprintjs/icons/lib/esm/generated/iconNames";
import PropTypes from "prop-types";

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
                        className="pt-minimal"
                        iconName={IconNames.INFO_SIGN}
                        onClick={handleInfoButtonClick}
                    />
                    <Dialog
                        iconName={IconNames.GRAPH}
                        isOpen={isInfoDialogOpen}
                        onClose={handleInfoDialogClose}
                        title="Graph"
                    >
                        <div className="pt-dialog-body">
                            Some content
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
        return (
            <Dialog
                iconName={IconNames.USER}
                isOpen={isUserDialogOpen}
                onClose={handleUserDialogClose}
                title={`${userData.screenName}`}
            >
                <div className="pt-dialog-body">
                    <b>Account created at:</b> {userData.createdAt}
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