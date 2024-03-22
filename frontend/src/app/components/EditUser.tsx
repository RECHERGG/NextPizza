import { useState } from "react";
import { editUserPassword, editUserWithName } from "../../../server/server";
import { useSearchParams } from "next/navigation";

export default function EditUser({name, lastName, uuid}: {name: string, lastName: string, uuid: string}) {
    const searchparams = useSearchParams();

    const [editName, setEditName] = useState<string>(name);
    const [editLastName, setEditLastName] = useState<string>(lastName);
    const [editUUID, setEditUUID] = useState<string>(uuid);
    const [showEditModal, setShowCreateModal] = useState<boolean>();

    function popup() {
        setShowCreateModal(!showEditModal);
    }

    async function handelSubmit() {
        const name = (document.getElementById("editName") as HTMLInputElement).value;
        const lastName = (document.getElementById("editLastName") as HTMLInputElement).value;
        const password = (document.getElementById("editPassword") as HTMLInputElement).value;
        const uuid = (document.getElementById("editUUID") as HTMLInputElement).value;
        if (name.length === 0 || lastName.length === 0 || uuid.length === 0) return;

        if (password === "") {//TODO password
            const data = await editUserWithName(name, lastName, uuid);
            if (data.status !== 200) return;
            popup();
        } else {
            const data = await editUserPassword(name, lastName, password, uuid);
            if (data.status !== 200) return;
            popup();
        }

        window.location.replace("/cp/user" + "?page=" + (searchparams.get("page") ?? "1"));
    }
    return (
        <div>
            <button onClick={popup} className="font-medium text-blue-600 dark:text-blue-500 hover:underline">Edit</button>

            <div id="edituser-modal" tabIndex={-1} aria-hidden="true" className={`${showEditModal ? "visible" : "hidden"} backdrop-blur-sm flex justify-center items-center overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 w-full md:inset-0 h-[calc(100%-1rem)] max-h-full`}>
                <div className="relative p-4 w-full max-w-md max-h-full">

                    <div className="relative bg-white rounded-lg shadow dark:bg-gray-700">

                        <div className="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                            <h3 className="text-xl font-semibold text-gray-900 dark:text-white">
                                Edit User
                            </h3>
                            <button onClick={popup} type="button" className="end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-hide="edituser-modal">
                                <svg className="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                                    <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6" />
                                </svg>
                                <span className="sr-only">Close modal</span>
                            </button>
                        </div>

                        <div className="p-4 md:p-5">
                            <div className="space-y-4">
                                <div>
                                    <label htmlFor="text" className="flex mb-2 text-sm font-medium text-gray-900 dark:text-white">Name</label>
                                    <input onChange={e => setEditName(e.target.value)} type="text" name="name" id="editName" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" value={editName} placeholder="Max" required />
                                </div>
                                <div>
                                    <label htmlFor="text" className="flex mb-2 text-sm font-medium text-gray-900 dark:text-white">Last Name</label>
                                    <input onChange={e => setEditLastName(e.target.value)} type="text" name="lastName" id="editLastName" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" value={editLastName} placeholder="Mustermann" required />
                                </div>
                                <div>
                                    <label htmlFor="text" className="flex mb-2 text-sm font-medium text-gray-900 dark:text-white">UUID</label>
                                    <input onChange={e => setEditUUID(e.target.value)}  type="text" name="uuid" id="editUUID" className="text-gray-500 dark:text-gray-400 bg-gray-50 border border-gray-300 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400" value={editUUID} disabled readOnly/>
                                </div>
                                <div>
                                    <label htmlFor="text" className="flex mb-2 text-sm font-medium text-gray-900 dark:text-white">Password</label>
                                    <input type="password" name="password" id="editPassword" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" placeholder="••••••••" />
                                </div>
                                <div className="space-x-4">
                                    <button onClick={handelSubmit} type="submit" className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Update</button>
                                    <button onClick={popup} type="submit" className="text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-800">Cancel</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}