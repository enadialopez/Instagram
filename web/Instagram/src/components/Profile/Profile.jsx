import React, { useState, useEffect} from "react";
import {useParams} from 'react-router-dom';
import Navbar from '../Navbar/Navbar'
import axios from "axios";
import '../../styles/Home.css';
import Api from "../../api/api";

const Profile = () => {
    const [user,setUser] = useState({
        id: "",
        name: "",
        image: "",
    })
    const [posts,setPosts] = useState([])

    function getUserId(id){
        Api.getUserById(id)
        .then(success=>{
            setPosts(success.data.posts)
        }).catch(error => {
            console.log(error) 
        });
    }


    useEffect(() => {    
            Api.getUser()
            .then(success =>{ 
               setUser({id:success.data.id,
                        name:success.data.name,
                        image:success.data.image})         

                getUserId(success.data.id)       
            })
            .catch(error =>
                console.log(error)
            )              
    }, []
    )

    
    console.log(user)

    return(
        <div>
            <Navbar />
            <div className="user text-center">
                <div className="imagePost">
                    <img className="imagePost" src={user.image}/>
                </div>
                <div className="nameUserPost">
                    <p>{user.name}</p>
                </div>
            </div>
            
            <div className="container-fluid">
                <div className="row">
                    {posts.map(post => (
                        <div className="card col-md-4 col-sm-12">
                            <div className="imageUserPost">
                                <img className="imageUserPost" src={post.landscape}/>
                            </div>
                        </div>    
                    ))}
                </div>
            </div>
        </div>    

    )
}

export default Profile ;
