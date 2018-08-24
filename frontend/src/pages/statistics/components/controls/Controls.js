import "./controls.scss";

import React from "react";
import PropTypes from "prop-types";
import KeywordInput from "./keyword-input/KeywordInput";
import RunButton from "./run-button/RunButton";
import StopButton from "./stop-button/StopButton";
import {Intent, ProgressBar} from "@blueprintjs/core";

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
                    onClick={this.props.handleRunButtonClick}
                />
                <StopButton
                    isDisabled={this.props.isStopButtonBlocked}
                    onClick={this.props.handleStopButtonClick}
                />
                <ProgressBar
                    intent={Intent.PRIMARY}
                    value={isKeywordInputBlocked && isRunButtonBlocked ? 1 : 0}
                />
            </div>
        );
    }

}
