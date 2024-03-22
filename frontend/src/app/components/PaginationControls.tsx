"use client";

import { useRouter, useSearchParams } from 'next/navigation'
import { User } from '../../../types';
import CreateUserModal from './CreateUserModal';

export default function PaginationControls({ users, per_page }: { users: User[], per_page: number }) {
    const searchParams = useSearchParams()

    const page = searchParams.get('page') ?? '1'

    const start = (Number(page) - 1) * Number(per_page);
    let end = start + Number(per_page);

    const lastPage = Math.ceil(users.length / Number(per_page));
    let nextPage = Number(page) + 1;
    let previousPage = Number(page) - 1;

    if (users.length < end) {
        end = users.length;
        nextPage = Number(page);
    }

    if (previousPage < 1) {
        previousPage = 1;
    }

    return (
        <div>
            <nav className="flex items-center flex-column flex-wrap md:flex-row justify-between mr-2 mt-2" aria-label="Table navigation">
                <span className="ml-4 text-sm font-normal text-gray-500 dark:text-gray-400 mb-4 md:mb-0 block w-full md:inline md:w-auto">Showing
                    <span className="font-semibold text-gray-900 dark:text-white"> {Number(page) * Number(per_page) - Number(per_page) + 1} - {end}</span> of
                    <span className="font-semibold text-gray-900 dark:text-white"> {users.length}</span></span>
                    <CreateUserModal />
                <ul className="inline-flex -space-x-px rtl:space-x-reverse text-sm h-8">
                    <li>
                        <a href={"/cp/user/?page=" + previousPage} className="flex items-center justify-center px-3 h-8 ms-0 leading-tight text-gray-500 bg-white border border-gray-300 rounded-s-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">Previous</a>
                    </li>
                    <li>
                        <a href={"/cp/user/?page=1"} className="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">1</a>
                    </li>
                    <li>
                        <a href={"/cp/user/?page=" + (Number(page))} aria-current="page" className="flex items-center justify-center px-3 h-8 text-blue-600 border border-gray-300 bg-blue-50 hover:bg-blue-100 hover:text-blue-700 dark:border-gray-700 dark:bg-gray-700 dark:text-white">{page}</a>
                    </li>
                    <li>
                        <a href={"/cp/user/?page=" + lastPage} className="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">{lastPage}</a>
                    </li>
                    <li>
                        <a href={"/cp/user/?page=" + nextPage} className="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 rounded-e-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">Next</a>
                    </li>
                </ul>
            </nav>
        </div>
    )
}
