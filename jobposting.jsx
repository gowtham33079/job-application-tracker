import React  from "react";
import './JobPosting.css';

import {}
export default class JobPosting extends Component {
    constructor() {
        super();
        this.state = {
            title :'',
             company:'' ,
              location:'',
              jobtype:'',
              salary:'',
              description:''

        };
        this.state = {joblist: []};
        
        componentDidMount(){
            callApi("GET",BASEURL +"jobs/read", "", this.readResponse)
        }
        readResponse(response)
        {
            if(response.includes("404::")){
                alert(response.split("::")[1]);
                return;
            }
            let data = JSON.parse(response);
            this.setState({jobList: data})
        }
    }
    loadInputchange(event)
    {
        this.setState({[event.target.name]: event.target.value});
    }
    saveJob()
    {
        let data=JSON.stringify(this.state);
        callApi("POST",BASEURL+"jobs/create",data, this.saveResponse);
    }
    saveResponse(Response)
    {
        let data = Response.split("::");
        alert(data[1]);
    }
    showPopup()
    {
        jppopup.style.display="block";

    }
    closePopup()
    {
        jppopup.style.display="none";
    }
            render () {
        const {title, company, location, jobtype, salary, description} = this.state;
        return (
            
            <div className='jpupcontainer'>
                <div id='jppopup' className="popup">
                    <div className="popupwindow">
                <div className='popupheader'>
                    <label> Popup Title</label>
                    <span> &times;</span>
                </div>
                <div className='popupcontent'></div>
                   <label> Job Title</label>
                   <input type='text' id="T1" name='title' value= {title} onChange={(title)=>this.loadInputchange(event)}></input>
                   <label> Company Name</label>
                   <input type='text' id="T2" name='company' value={company} onChange={(title)=>this.loadInputchange(event)}></input>
                   <label> Location</label>
                   <input type='text' id="T3"name='location' value={location} onChange={(title)=>this.loadInputchange(event)}></input>
                   <label> Job Type*</label>
                   <select id="T4" name='jobtype' value={jobtype} onChange={(title)=>this.loadInputchange(event)}>
                   <option value="0"></option>
                   <option value="1"> Full-Time</option>
                   <option value="2">Part-Time</option>
                   </select>
                   <label> Salary</label>
                   <input type='text' id="T5" name='salary' value={salary} onChange={(title)=>this.loadInputchange(event)}></input>
                   <label> Job Description*</label>
                   <textarea id="T6" rows="5" name='description' value={description} onChange={(title)=>this.loadInputchange(event)}></textarea>

                     <button onClick={()=>this.saveJob()}> save</button>
           
               
                </div>
                </div>
                
                <div className='popupfooter'></div>
                <div className='header'></div>
                <div className='content'></div>
                {jobListr.map((data)=>(
                    <div className='result'>
                    <div className='div1'>
                    <label>{data.title}</label>       
                    <span>{data.salary}</span>
                    <img src='/delete.png' alt=''></img>
                    <img src='/edit.png' alt=''></img>
                    </div>
                    <div className='div2'>
                    {<data value="" className="company"></data></div>
                ))}
                <div className='footer'></div>
                <button onClick={()=>this.showPopup()}> Add New </button>
            </div>       
             )
    }

}