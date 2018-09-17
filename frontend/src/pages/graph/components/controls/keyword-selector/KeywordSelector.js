import "./keyword-selector.scss"

import React from "react";
import PropTypes from "prop-types";
import _ from "lodash";

export default class KeywordSelector extends React.Component {

    static propTypes = {
        onChange: PropTypes.func,
        keywords: PropTypes.array
    };

    static defaultProps = {
        onChange: _.noop(),
        keywords: []
    };

    handleChange(event) {
        this.props.onChange(event.target.value);
    }

    render() {
        return (
            <div className="pt-select">
                <select onChange={this.handleChange.bind(this)}>
                    {this.prepareKeywords()}
                </select>
            </div>
        );
    }

    prepareKeywords() {
        const {keywords} = this.props;
        return _.map(keywords, (keyword) =>
            <option
                key={keyword.name}
                value={keyword.name}
            >
                {keyword.name}
            </option>
        );
    }

}
