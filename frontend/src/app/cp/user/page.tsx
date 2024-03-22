import UserTable from "@/app/components/UserTable";
import { User } from "../../../../types/index"
import { users } from "../../../../server/server";

export async function getAllUsers() {
    let data = await users();
    const u: User[] = data.data.users;

    return u;
}

export default async function User() {
    const users = await getAllUsers();

    return (
        <div className="mt-6 ml-6">
            <h1 className="text-2xl font-semibold">User</h1>
            <div className="mt-8">
                 <UserTable users={users} />
            </div>
        </div>
    )
}