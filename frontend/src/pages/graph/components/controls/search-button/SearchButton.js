import React from "react";
import PropTypes from "prop-types";

export default class SearchButton extends React.Component {

    static propTypes = {
        onClick: PropTypes.func.isRequired,
    };

    render() {
        return (
            <button
                className="pt-button pt-intent-success"
                type="button"
                onClick={this.props.onClick}
            >
                Search
            </button>
        );
    }

}
