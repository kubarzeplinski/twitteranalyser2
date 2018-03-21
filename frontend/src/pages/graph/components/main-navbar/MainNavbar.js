import React from "react";
import {Button, Navbar, NavbarDivider, NavbarGroup, NavbarHeading} from "@blueprintjs/core";

export default class MainNavbar extends React.Component {

    render() {
        return (
            <Navbar>
                <NavbarGroup>
                    <NavbarHeading>Twitter Analyser</NavbarHeading>
                    <NavbarDivider />
                </NavbarGroup>
                <NavbarGroup align="right">
                    <Button className="pt-minimal" iconName="user"/>
                    <Button className="pt-minimal" iconName="notifications"/>
                    <Button className="pt-minimal" iconName="cog"/>
                </NavbarGroup>
            </Navbar>
        );
    }

}
