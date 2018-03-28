import React from "react";
import MainNavbar from "../pages/graph/components/main-navbar/MainNavbar";
import Graph from "../pages/graph/components/graph/Graph";
import KeywordInput from "../pages/graph/components/keyword-input/KeywordInput";
import RunButton from "../pages/graph/components/run-button/RunButton";
import StopButton from "../pages/graph/components/stop-button/StopButton";

export default class App extends React.Component {

    render() {
        return (
            <div>
                <MainNavbar/>
                <div className="panel-content">
                    <KeywordInput/>
                    <RunButton/>
                    <StopButton/>
                    <Graph/>
                </div>
            </div>
        );
    }

}
