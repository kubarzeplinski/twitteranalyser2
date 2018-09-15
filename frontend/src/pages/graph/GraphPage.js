import Controls from "./components/controls/ControlsContainer";
import Graph from "./components/graph/Graph";
import React from "react";
import {Button, Dialog} from "@blueprintjs/core";
import * as IconNames from "@blueprintjs/icons/lib/esm/generated/iconNames";
import PropTypes from "prop-types";

export default class GraphPage extends React.Component {

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
                </h4>
                <Controls/>
                <Graph/>
            </div>
        );
    }

}