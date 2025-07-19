import React, { Component } from 'react'
import '.Dashbaord.css'
import MenuBar from './MenuBar';
class Dashboard extends Component {
    render() {
        return (
            <div classname='dashboard'>
                <div classname = 'header'>Header
                    <img classname='logo' src='/logo.png' alt=''></img>
                     </div>
                     <div classname ='logoText'> job <span> Quest </span></div>
                     <img classname = 'logout' src='/logout'
                <div classname = 'menu'>
                    < MenuBar></MenuBar></div>  
                <div classname = 'outlet'>Outlet</div>  
                </div>
        );
    }
}

export default Dashboard;