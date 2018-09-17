import "./main-navbar.scss";

import React from "react";
import PropTypes from 'prop-types';
import {Button, Intent, Navbar, NavbarDivider, NavbarGroup, NavbarHeading, Tag} from "@blueprintjs/core";
import {Link} from "react-router-dom";
import {IconNames} from "@blueprintjs/icons";
import classNames from "classnames";

export default class MainNavbar extends React.Component {

    static propTypes = {
        isProcessingOn: PropTypes.bool,
        keyword: PropTypes.string
    };

    render() {
        return (
            <Navbar className="main-navbar">
                <NavbarGroup>
                    <Link to="/">
                        <NavbarHeading>Twitter Analyser</NavbarHeading>
                    </Link>
                    <NavbarDivider className="navbar-divider"/>
                    <Link to="/statistics">
                        <Button className="pt-minimal navbar-icon" iconName={IconNames.CHART} text="Statistics"/>
                    </Link>
                    <Link to="/graph">
                        <Button className="pt-minimal navbar-icon" iconName={IconNames.GRAPH} text="Graph"/>
                    </Link>
                </NavbarGroup>
                <NavbarGroup align="right">
                    {this.prepareKeyWordTag()}
                </NavbarGroup>
            </Navbar>
        );
    }

    prepareKeyWordTag() {
        const {isProcessingOn, keyword} = this.props;
        if (isProcessingOn) {
            const className = classNames("pt-large", { "pulsing-keyword-tag": isProcessingOn });
            return (
                <Tag className={className} intent={Intent.SUCCESS}>
                    <span>Live analysis for keyword: {keyword}</span>
                </Tag>
            );
        }
    }

}
