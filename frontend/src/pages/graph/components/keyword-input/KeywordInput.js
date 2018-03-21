import "./keyword-input.scss"

import React from "react";

export default class KeywordInput extends React.Component {

    render() {
        return (
            <input className="pt-input .modifier keyword-input" type="text" placeholder="Keyword" dir="auto"/>
        );
    }

}
