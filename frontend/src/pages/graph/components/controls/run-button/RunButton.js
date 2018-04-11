import React from "react";
import PropTypes from "prop-types";

export default class RunButton extends React.Component {

    static propTypes = {
        isDisabled: PropTypes.bool,
        onClick: PropTypes.func.isRequired,
    };

    static defaultProps = {
        isDisabled: false,
    };

    render() {
        return (
            <button
                className="pt-button pt-intent-success"
                disabled={this.props.isDisabled}
                type="button"
                onClick={this.props.onClick}
            >
                Run
            </button>
        );
    }

}
