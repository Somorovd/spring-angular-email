import { Address } from './address.interface';
import { Email } from './email.interface';

export interface Status {
  id: number;
  address: Address;
  chainId: number;
  email: Email;
  isRead: boolean;
  isStarred: boolean;
  location: string;
}
