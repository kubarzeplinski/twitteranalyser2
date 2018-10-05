import React from "react";
import PropTypes from "prop-types";

export default class SearchButton extends React.Component {

    static propTypes = {
        onClick: PropTypes.func.isRequired,
    };

    render() {
        return (
            <button
                className="bp3-button bp3-intent-success"
                type="button"
                onClick={this.props.onClick}
            >
                Search
            </button>
        );
    }

}
