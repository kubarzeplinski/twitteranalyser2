import "./main-navbar.scss";

import React from "react";
import {Button, Navbar, NavbarDivider, NavbarGroup, NavbarHeading} from "@blueprintjs/core";
import {Link} from "react-router-dom";
import {IconNames} from "@blueprintjs/icons";

export default class MainNavbar extends React.Component {

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
                    <Button className="pt-minimal navbar-icon" iconName={IconNames.USER}/>
                    <Button className="pt-minimal navbar-icon" iconName={IconNames.NOTIFICATIONS}/>
                    <Button className="pt-minimal navbar-icon" iconName={IconNames.COG}/>
                </NavbarGroup>
            </Navbar>
        );
    }

}
