import "./controls.scss";

import React from "react";
import PropTypes from "prop-types";
import {Intent, ProgressBar, Toaster} from "@blueprintjs/core";
import {IconNames} from "@blueprintjs/icons";
import KeywordInput from "./keyword-input/KeywordInput";
import RunButton from "./run-button/RunButton";
import StopButton from "./stop-button/StopButton";

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
        this.toaster = React.createRef();
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
                <Toaster ref={this.toaster}/>
            </div>
        );
    }

    handleRunButtonClick() {
        this.props.handleRunButtonClick();
        this.toaster.current.show({
            intent: Intent.SUCCESS,
            message: "Analysis with keyword " + this.props.keyword + " started.",
            icon: IconNames.TICK
        });
    }

    handleStopButtonClick() {
        this.props.handleStopButtonClick();
        this.toaster.current.show({
            intent: Intent.DANGER,
            message: "Analysis stopped.",
            icon: IconNames.WARNING_SIGN
        });
    }

}
