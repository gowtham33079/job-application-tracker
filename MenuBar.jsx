import React, {Component} from 'react'
import './MenuBar.css'
export default class MenuBar extends Component {
    render() {
        <div className='menuheader'>MENU<img src= '/menu.png' alt= ''> </img> </div>
        <div className='menulist'>
            <ul>
                <li>menu Item1 <img src='/list.png' alt=''></img></li>
                <li> Menu Item <img src='/list.png' alt=''></img></li>
                <li> Menu Item <img src='/list.png' alt=''></img></li>
            </ul>
        </div>
    }
}