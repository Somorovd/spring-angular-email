import { StatusListResponseInterface } from './statusListResponse.interface';

export interface MailStateInterface {
  inbox: StatusListResponseInterface;
  isLoading: boolean;
  error: string | null;
}
