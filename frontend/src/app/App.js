import React from "react";
import MainNavbar from "../pages/graph/components/main-navbar/MainNavbar";
import Graph from "../pages/graph/components/graph/Graph";
import Controls from "../pages/graph/components/controls/ControlsContainer";

export default class App extends React.Component {

    render() {
        return (
            <div>
                <MainNavbar/>
                <div className="panel-content">
                    <Controls/>
                    <Graph/>
                </div>
            </div>
        );
    }

}
