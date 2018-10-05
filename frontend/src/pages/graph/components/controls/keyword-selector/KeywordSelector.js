import "./keyword-selector.scss"

import React from "react";
import PropTypes from "prop-types";
import _ from "lodash";
import {Select} from "@blueprintjs/select";
import {Button} from "@blueprintjs/core";
import {IconNames} from "@blueprintjs/icons";

export default class KeywordSelector extends React.Component {

    static propTypes = {
        onChange: PropTypes.func,
        keywords: PropTypes.array,
        keyword: PropTypes.string
    };

    static defaultProps = {
        onChange: _.noop(),
        keywords: []
    };

    render() {
        return (
            <div className="keyword-selector">
                <Select
                    items={this.props.keywords}
                    itemRenderer={(keyword) => this.keywordRenderer(keyword)}
                    onItemSelect={() => this}
                    itemPredicate={this.filterItems}
                    resetOnSelect={true}
                >
                    <Button
                        icon={IconNames.KEY}
                        rightIcon={IconNames.CARET_DOWN}
                        text={this.props.keyword ? this.props.keyword : "No selection"}
                    />
                </Select>
            </div>
        );
    }

    keywordRenderer(keyword) {
        return (
            <li key={keyword.name} onClick={this.handleChange.bind(this, keyword.name)}>
                <a className="bp3-menu-item">
                    <div className="bp3-text-overflow-ellipsis bp3-fill">{keyword.name}</div>
                </a>
            </li>
        );
    }

    filterItems(query, keyword) {
        return keyword.name.toLowerCase().indexOf(query.toLowerCase()) >= 0;
    }

    handleChange(keyword) {
        this.props.onChange(keyword);
    }

}
