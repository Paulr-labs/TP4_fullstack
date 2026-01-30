import api from './api'

export interface CVItem {
  id: number
  name: string
  url: string
  uploadedAt?: string
}

export async function getCVs(): Promise<CVItem[]> {
  const res = await api.get('/cvs')
  return res.data
}

export async function getCV(id: number): Promise<CVItem> {
  const res = await api.get(`/cvs/${id}`)
  return res.data
}

export default { getCVs, getCV }
