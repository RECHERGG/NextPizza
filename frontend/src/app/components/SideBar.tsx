"use client";

import { TfiDashboard } from "react-icons/tfi";
import { FaUserEdit } from "react-icons/fa";

const SideBar = () => {
    return (
        <div className="flex-col px-6 py-8 mx-auto md:h-screen lg:py-0">
            <a href="/cp/dashboard" className="flex items-center ml-12 mt-6 mb-8 text-2xl font-semibold text-gray-900 dark:text-white">
                <img className="w-8 h-8 mr-2" src="../favicon.ico" alt="logo" />
                NextPizza
            </a>


            <div className="h-[90vh] w-64 mb-26 bg-white rounded-lg shadow dark:border dark:bg-gray-800 dark:border-gray-700">
                <div className="text-2xl font-medium">
                    <a href="/cp/dashboard" className="flex space-x-4 items-center gap-2.5 p-10 hover:bg-gray-600 rounded-lg">
                        <TfiDashboard />
                        <div>
                            Dashboard
                        </div>
                    </a>
                    <a href="/cp/user" className="flex space-x-4 items-center gap-2.5 p-10 hover:bg-gray-600 rounded-lg">
                        <FaUserEdit />
                        <div>
                            User
                        </div>
                    </a>
                </div>
            </div>
        </div>
    );
}

export default SideBar