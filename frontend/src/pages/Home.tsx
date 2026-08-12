import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useUsers } from '../hooks/useUsers';

export default function Home() {
  const navigate = useNavigate();
  const { data, loading, error, fetch, fetchById } = useUsers();
  const [username, setUsername] = useState('');
  const [userId, setUserId] = useState('');

  if(!localStorage.getItem("token")){
    navigate('/login')
  }

  useEffect(() => {
    fetch(0, username || undefined);
  }, [username, fetch]);

  const handleSearch = () => {
    if (userId) {
      const id = parseInt(userId, 10);
      if (!isNaN(id)) {
        fetchById(id);
      }
    }
  };

  const handleClearSearch = () => {
    setUserId('');
    setUsername('');
    fetch(0);
  };

  const goToPage = (page: number) => {
    fetch(page, username || undefined);
  };

  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <div className="max-w-6xl mx-auto">
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-3xl font-bold text-gray-900">Users</h1>
          <button
            onClick={() => navigate('/register')}
            className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
          >
            Register User
          </button>
        </div>

        <div className="bg-white p-6 rounded-lg shadow mb-6">
          <div className="grid grid-cols-3 gap-4 mb-4">
            <div>
              <label className="block text-gray-700 font-medium mb-2">Search by Username</label>
              <input
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                placeholder="Enter username"
                className="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>

            <div>
              <label className="block text-gray-700 font-medium mb-2">Search by ID</label>
              <input
                type="number"
                value={userId}
                onChange={(e) => setUserId(e.target.value)}
                placeholder="Enter user ID"
                className="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>

            <div className="flex items-end gap-2">
              <button
                onClick={handleSearch}
                className="flex-1 bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
              >
                Search ID
              </button>
              <button
                onClick={handleClearSearch}
                className="flex-1 bg-gray-400 text-white px-4 py-2 rounded hover:bg-gray-500"
              >
                Clear
              </button>
            </div>
          </div>
        </div>

        {error && <div className="mb-4 p-4 bg-red-100 text-red-700 rounded">{error}</div>}

        {loading ? (
          <div className="text-center py-8 text-gray-500">Loading...</div>
        ) : data?.content && data.content.length > 0 ? (
          <>
            <div className="bg-white rounded-lg shadow overflow-hidden">
              <table className="w-full">
                <thead className="bg-gray-100 border-b">
                  <tr>
                    <th className="px-6 py-3 text-left text-sm font-medium text-gray-700">ID</th>
                    <th className="px-6 py-3 text-left text-sm font-medium text-gray-700">
                      Username
                    </th>
                    <th className="px-6 py-3 text-left text-sm font-medium text-gray-700">
                      Email
                    </th>
                  </tr>
                </thead>
                <tbody>
                  {data.content.map((user) => (
                    <tr key={user.id} className="border-b hover:bg-gray-50">
                      <td className="px-6 py-4 text-sm text-gray-900">{user.id}</td>
                      <td className="px-6 py-4 text-sm text-gray-900">{user.username}</td>
                      <td className="px-6 py-4 text-sm text-gray-900">{user.email}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>

            {data.totalPages > 1 && (
              <div className="flex justify-center items-center gap-2 mt-6">
                <button
                  onClick={() => goToPage(data.currentPage - 1)}
                  disabled={data.currentPage === 0}
                  className="px-4 py-2 border border-gray-300 rounded disabled:bg-gray-100 disabled:text-gray-400"
                >
                  Previous
                </button>
                <span className="text-gray-700 font-medium">
                  Page {data.currentPage + 1} of {data.totalPages}
                </span>
                <button
                  onClick={() => goToPage(data.currentPage + 1)}
                  disabled={data.currentPage >= data.totalPages - 1}
                  className="px-4 py-2 border border-gray-300 rounded disabled:bg-gray-100 disabled:text-gray-400"
                >
                  Next
                </button>
              </div>
            )}
          </>
        ) : (
          <div className="text-center py-8 text-gray-500">No users found</div>
        )}
      </div>
    </div>
  );
}
