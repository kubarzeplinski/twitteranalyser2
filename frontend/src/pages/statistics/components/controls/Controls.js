import "./controls.scss";

import React from "react";
import PropTypes from "prop-types";
import KeywordInput from "./keyword-input/KeywordInput";
import RunButton from "./run-button/RunButton";
import StopButton from "./stop-button/StopButton";
import {Intent, ProgressBar, Toaster} from "@blueprintjs/core";

export default class Controls extends React.Component {

    static propTypes = {
        handleKeywordAdd: PropTypes.func,
        handleRunButtonClick: PropTypes.func,
        handleStopButtonClick: PropTypes.func,
        isKeywordInputBlocked: PropTypes.bool,
        isRunButtonBlocked: PropTypes.bool,
        isStopButtonBlocked: PropTypes.bool,
        keyword: PropTypes.string,
    };

    constructor(props) {
        super(props);
        this.runToaster = React.createRef();
        this.stopToaster = React.createRef();
    }

    render() {
        const {isKeywordInputBlocked, isRunButtonBlocked} = this.props;
        return (
            <div>
                <KeywordInput
                    isDisabled={isKeywordInputBlocked}
                    onChange={this.props.handleKeywordChange}
                    placeholder={"Keyword..."}
                    value={this.props.keyword}
                />
                <RunButton
                    isDisabled={isRunButtonBlocked}
                    onClick={this.handleRunButtonClick.bind(this)}
                />
                <StopButton
                    isDisabled={this.props.isStopButtonBlocked}
                    onClick={this.handleStopButtonClick.bind(this)}
                />
                <ProgressBar
                    intent={Intent.PRIMARY}
                    value={isKeywordInputBlocked && isRunButtonBlocked ? 1 : 0}
                />
                <Toaster ref={this.runToaster}/>
                <Toaster ref={this.stopToaster}/>
            </div>
        );
    }

    handleRunButtonClick() {
        this.props.handleRunButtonClick();
        const key = this.runToaster.current.show();
        this.runToaster.current.update(
            key,
            {
                intent: Intent.SUCCESS,
                message: "Analysis with keyword " + this.props.keyword + " started."
            }
        );
    }

    handleStopButtonClick() {
        this.props.handleStopButtonClick();
        const key = this.stopToaster.current.show();
        this.stopToaster.current.update(
            key,
            {
                intent: Intent.DANGER,
                message: "Analysis stopped."
            }
        );
    }

}
