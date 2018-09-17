import React from "react";
import PropTypes from "prop-types";
import KeywordSelector from "./keyword-selector/KeywordSelector";
import SearchButton from "./search-button/SearchButton";

export default class Controls extends React.Component {

    static propTypes = {
        handleSearchButtonClick: PropTypes.func,
        handleKeywordChange: PropTypes.func,
        keyword: PropTypes.string,
        keywords: PropTypes.array
    };

    render() {
        return (
            <div>
                <KeywordSelector
                    onChange={this.props.handleKeywordChange}
                    keywords={this.props.keywords}
                />
                <SearchButton
                    onClick={() => this.handleSearchButtonClick()}
                />
            </div>
        );
    }

    handleSearchButtonClick() {
        const {handleSearchButtonClick, keyword} = this.props;
        handleSearchButtonClick(keyword);
    }

}
