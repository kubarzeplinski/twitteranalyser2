import "./keyword-input.scss"

import React from "react";
import PropTypes from "prop-types";
import _ from "lodash";

export default class KeywordInput extends React.Component {

    static propTypes = {
        isDisabled: PropTypes.bool,
        onChange: PropTypes.func,
        placeholder: PropTypes.string,
        value: PropTypes.string
    };

    static defaultProps = {
        isDisabled: false,
        onChange: _.noop(),
        placeholder: "",
        value: ""
    };

    handleChange(event) {
        this.props.onChange(event.target.value);
    }

    render() {
        return (
            <input
                className="bp3-input .modifier keyword-input"
                dir="auto"
                disabled={this.props.isDisabled}
                onChange={this.handleChange.bind(this)}
                placeholder={this.props.placeholder}
                type="text"
                value={this.props.value}
            />
        );
    }

}
