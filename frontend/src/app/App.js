import React from "react";
import webstomp from "webstomp-client";
import SockJS from "sockjs-client";
import MainNavbar from "../pages/graph/components/main-navbar/MainNavbar";
import Graph from "../pages/graph/components/graph/Graph";
import KeywordInput from "../pages/graph/components/keyword-input/KeywordInput";
import RunButton from "../pages/graph/components/run-button/RunButton";
import StopButton from "../pages/graph/components/stop-button/StopButton";

export default class App extends React.Component {

    connect() {
        const socket = new SockJS('http://localhost:8080/twitter-analyser');
        const stompClient = webstomp.over(socket);
        stompClient.connect({}, (frame) => {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/dashboard/graphData', function (data) {
                //TODO send data to graph
            });
        });
    };

    componentDidMount() {
        this.connect();
    }

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
