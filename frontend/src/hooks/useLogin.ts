import { useState } from 'react';
import { api } from '../services/api.service';

export function useLogin() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const login = async (username: string, password: string) => {
    setLoading(true);
    setError(null);
    try {
      const result = await api.login(username, password);
      localStorage.setItem('token', result.token || 'authenticated');
      return result;
    } catch (err) {
      const msg = err instanceof Error ? err.message : 'Login failed';
      setError(msg);
      throw err;
    } finally {
      setLoading(false);
    }
  };

  return { login, loading, error };
}
