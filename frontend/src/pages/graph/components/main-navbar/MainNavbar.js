import "./main-navbar.scss";

import React from "react";
import {Button, Navbar, NavbarDivider, NavbarGroup, NavbarHeading} from "@blueprintjs/core";

export default class MainNavbar extends React.Component {

    render() {
        return (
            <Navbar className="main-navbar">
                <NavbarGroup>
                    <NavbarHeading>Twitter Analyser</NavbarHeading>
                    <NavbarDivider className="navbar-divider"/>
                </NavbarGroup>
                <NavbarGroup align="right">
                    <Button className="pt-minimal navbar-icon" iconName="user"/>
                    <Button className="pt-minimal navbar-icon" iconName="notifications"/>
                    <Button className="pt-minimal navbar-icon" iconName="cog"/>
                </NavbarGroup>
            </Navbar>
        );
    }

}
