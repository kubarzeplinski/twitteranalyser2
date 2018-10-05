import React from "react";
import PropTypes from "prop-types";

export default class StopButton extends React.Component {

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
                type="button"
                className="bp3-button bp3-intent-danger"
                disabled={this.props.isDisabled}
                onClick={this.props.onClick}
            >
                Stop
            </button>
        );
    }

}
