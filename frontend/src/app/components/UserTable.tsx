"use client";

import { useSearchParams } from "next/navigation";
import { User } from "../../../types/index"
import PaginationControls from "./PaginationControls";
import EditUser from "./EditUser";

export default function UserTable({ users }: { users: User[] }) {
    const searchParams = useSearchParams();

    const page = searchParams.get('page') ?? '1'
    const per_page = 11;

    const start = (Number(page) - 1) * Number(per_page);
    const end = start + Number(per_page);

    const userArry = users.slice(Number(page) * Number(per_page) - Number(per_page), end);

    return (
        <div>
            <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                    <tr>
                        <td className="rounded-tl-lg text-center py-3">Name</td>
                        <td className="text-center">Last Name</td>
                        <td className="text-center">UUID</td>
                        <td className="text-center">Created At</td>
                        <td className="rounded-tr-lg text-center py-3">Action</td>
                    </tr>
                </thead>
                <tbody>
                    {userArry.map((user: User) => {
                        return <tr key={user.uuid} className="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
                            <th className="text-center px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">{user.name}</th>
                            <th className="text-center py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">{user.lastName}</th>
                            <td className="text-center">{user.uuid}</td>
                            <td className="text-center">{new Date(user.created).toLocaleString()}</td>
                            <td className="text-center">
                                <EditUser name={user.name} lastName={user.lastName} uuid={user.uuid}/></td>
                        </tr>
                    })}
                </tbody>
            </table>

            <PaginationControls users={users} per_page={per_page}/>
        </div>
    );
}