import axios from "axios";
import {wrapper} from "axios-cookiejar-support";
import {CookieJar} from "tough-cookie";

export default async function cleanup() {
    const jar = new CookieJar()
    const client = wrapper(axios.create({ jar, withCredentials: true}))

    await client.post("http://localhost:8080/user/login", {
        username: "D5h6q758eViTo3LRPT0G",
        password: "D5h6q758eViTo3LRPT0G",
    })

    await client.get("http://localhost:8080/user/cleanup")
}