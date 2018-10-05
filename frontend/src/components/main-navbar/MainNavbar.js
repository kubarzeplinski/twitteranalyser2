import "./main-navbar.scss";

import React from "react";
import PropTypes from 'prop-types';
import {Button, Icon, Intent, Navbar, NavbarDivider, NavbarGroup, NavbarHeading, Tag} from "@blueprintjs/core";
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
                        <Button className="bp3-minimal navbar-icon" text="Statistics">
                            <Icon icon={IconNames.CHART} iconSize={Icon.SIZE_STANDARD} color="#FFFFFF"/>
                        </Button>
                    </Link>
                    <Link to="/graph">
                        <Button className="bp3-minimal navbar-icon" text="Graph">
                            <Icon icon={IconNames.GRAPH} iconSize={Icon.SIZE_STANDARD} color="#FFFFFF"/>
                        </Button>
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
            const className = classNames("bp3-large", { "pulsing-keyword-tag": isProcessingOn });
            return (
                <Tag className={className} intent={Intent.SUCCESS}>
                    <span>Live analysis for keyword: {keyword}</span>
                </Tag>
            );
        }
    }

}
