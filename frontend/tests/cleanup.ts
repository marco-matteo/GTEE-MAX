import axios from "axios";
import * as fs from "fs"

export default async function cleanup() {
    fs.writeFileSync("./test.log", "executed")
    await axios.get("http://localhost:8080/user/cleanup")
}