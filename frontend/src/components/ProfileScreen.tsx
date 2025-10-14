import {useNavigate, useParams} from "react-router";
import {useEffect, useState} from "react";
import axios from "axios";

type User = {
    username: string,
    password: string,
    views: number,
    favorite_id: number,
}

export default function ProfileScreen() {
    const {username} = useParams<{ username: string }>()
    const navigate = useNavigate();
    const [user, setUser] = useState<User | null>(null)
    const [loading, setLoading] = useState<boolean>(true)

    useEffect(() => {
        if (!username) return

        axios.get(`http://localhost:8080/auth/user/check/${username}`)
            .then(() => {
                return axios.get(`http://localhost:8080/user/${username}`, {withCredentials: true})
            })
            .then((res) => {
                setUser(res.data);
            })
            .catch((err) => {
                if (err.response.status === 404) {
                    navigate("/404");
                } else {
                    console.error("Error loading user", err)
                }
            })
            .finally(() => setLoading(false))
    }, [username, navigate])

    if (loading) return <div>Loading...</div>
    if (!user) return null

    return (
        <div>
            <h1>@{user.username}</h1>
        </div>
    )
}