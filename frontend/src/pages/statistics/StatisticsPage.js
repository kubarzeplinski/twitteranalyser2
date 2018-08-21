import React from "react";
import PropTypes from 'prop-types';
import Controls from "./components/controls/ControlsContainer";
import {IconNames} from "@blueprintjs/icons";
import {Button, Card, Dialog, Elevation, Intent, Tag} from "@blueprintjs/core";

export default class StatisticsPage extends React.Component {

    static propTypes = {
        isInfoDialogOpen: PropTypes.bool,
        handleInfoButtonClick: PropTypes.func,
        handleInfoDialogClose: PropTypes.func
    };

    render() {
        const {handleInfoButtonClick, isInfoDialogOpen, handleInfoDialogClose} = this.props;
        return (
            <div className="panel-content">
                <h4>
                    Statistics
                    <Button
                        className="pt-minimal"
                        iconName={IconNames.INFO_SIGN}
                        onClick={handleInfoButtonClick}
                    />
                    <Dialog
                        iconName={IconNames.CHART}
                        isOpen={isInfoDialogOpen}
                        onClose={handleInfoDialogClose}
                        title="Statistics"
                    >
                        <div className="pt-dialog-body">
                            Some content
                        </div>
                    </Dialog>
                </h4>
                <Controls/>
                <div>
                    <Card elevation={Elevation.TWO}>
                        <h4>Latest 5 users</h4>
                        <Tag className="pt-large" intent={Intent.PRIMARY}>
                            <span>ABC</span>
                        </Tag>
                        <Tag className="pt-large" intent={Intent.PRIMARY}>
                            <span>Tom Jones</span>
                        </Tag>
                        <Tag className="pt-large" intent={Intent.PRIMARY}>
                            <span>James Bond</span>
                        </Tag>
                        <Tag className="pt-large" intent={Intent.PRIMARY}>
                            <span>James Bond</span>
                        </Tag>
                        <Tag className="pt-large" intent={Intent.PRIMARY}>
                            <span>Monica Belluci</span>
                        </Tag>
                    </Card>
                    <Card elevation={Elevation.TWO}>
                        <h4>Number of users</h4>
                        <Tag className="pt-large">
                            <span>12345</span>
                        </Tag>
                    </Card>
                </div>
                <div>
                    <Card elevation={Elevation.TWO}>
                        <h4>Top 5 locations</h4>
                        <Tag className="pt-large" intent={Intent.SUCCESS}>
                            <span>New York</span>
                        </Tag>
                        <Tag className="pt-large" intent={Intent.SUCCESS}>
                            <span>Warsaw</span>
                        </Tag>
                        <Tag className="pt-large" intent={Intent.SUCCESS}>
                            <span>Zakopane</span>
                        </Tag>
                        <Tag className="pt-large" intent={Intent.SUCCESS}>
                            <span>Rome</span>
                        </Tag>
                        <Tag className="pt-large" intent={Intent.SUCCESS}>
                            <span>Sydney</span>
                        </Tag>
                    </Card>
                    <Card elevation={Elevation.TWO}>
                        <h4>Top 5 users by followers</h4>
                        <Tag className="pt-large" intent={Intent.WARNING}>
                            <span>John Kowalski</span>
                        </Tag>
                        <Tag className="pt-large" intent={Intent.WARNING}>
                            <span>James Bond</span>
                        </Tag>
                        <Tag className="pt-large" intent={Intent.WARNING}>
                            <span>Mark Twain</span>
                        </Tag>
                        <Tag className="pt-large" intent={Intent.WARNING}>
                            <span>Michael Nowak</span>
                        </Tag>
                        <Tag className="pt-large" intent={Intent.WARNING}>
                            <span>Johnny Bravo</span>
                        </Tag>
                    </Card>
                </div>
            </div>
        );
    }

}