import { Status } from '../../../types/status.interface';

export interface InboxStateInterface {
  statuses: Status[];
  count: number;
}
