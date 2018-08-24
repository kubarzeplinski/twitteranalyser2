import React from "react";
import PropTypes from "prop-types";
import KeywordInput from "./keyword-input/KeywordInput";
import SearchButton from "./search-button/SearchButton";

export default class Controls extends React.Component {

    static propTypes = {
        handleKeywordAdd: PropTypes.func,
        handleSearchButtonClick: PropTypes.func,
        isKeywordInputBlocked: PropTypes.bool,
        isSearchButtonBlocked: PropTypes.bool,
        keyword: PropTypes.string
    };

    render() {
        return (
            <div>
                <KeywordInput
                    isDisabled={this.props.isKeywordInputBlocked}
                    onChange={this.props.handleKeywordChange}
                    placeholder={"Keyword..."}
                    value={this.props.keyword}
                />
                <SearchButton
                    isDisabled={this.props.isSearchButtonBlocked}
                    onClick={this.props.handleSearchButtonClick}
                />
            </div>
        );
    }

}
