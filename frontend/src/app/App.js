import React from "react";
import MainNavbar from "../components/main-navbar/MainNavbarContainer";

export default class App extends React.Component {

    render() {
        return (
            <div>
                <MainNavbar/>
                {this.props.children}
            </div>
        );
    }

}
