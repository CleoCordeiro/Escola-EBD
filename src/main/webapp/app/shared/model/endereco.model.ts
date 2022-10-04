import { IResponsavel } from 'app/shared/model/responsavel.model';
import { IPessoa } from 'app/shared/model/pessoa.model';

export interface IEndereco {
  id?: string;
  logradouro?: string;
  numero?: string;
  complemento?: string;
  bairro?: string;
  cidade?: string;
  estado?: string;
  cep?: string;
  responsaveis?: IResponsavel[] | null;
  pessoas?: IPessoa[] | null;
}

export const defaultValue: Readonly<IEndereco> = {};
